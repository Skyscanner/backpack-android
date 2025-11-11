#!/usr/bin/env bash
#
# Backpack Android - AI Work Labels Pre-Commit Hook
#
# Purpose: Automatically extracts AI work labels from Log.<tool>.json files,
#          appends them to commit messages with 'ai:' prefix, and cleans up log files.
#
# Self-Installing: Auto-configures on first run, no manual setup required.
#
# Usage: Called automatically by git prepare-commit-msg hook
#

set -euo pipefail

# =============================================================================
# Configuration
# =============================================================================

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
CONFIG_DEFAULT="${SCRIPT_DIR}/config.default.yaml"
CONFIG_FILE="${SCRIPT_DIR}/config.yaml"
INSTALLED_MARKER="${SCRIPT_DIR}/.installed"
LOG_FILE_PATTERN="Log.*.json"

# Always search for log files in git repository root (not current directory)
GIT_ROOT="$(git rev-parse --show-toplevel 2>/dev/null || pwd)"

# Label prefix from config (defaults to "ai: ")
LABEL_PREFIX="ai: "

# Error tracking for detailed failure messages
declare -a ERROR_MESSAGES=()

# =============================================================================
# Logging Functions
# =============================================================================

log_info() {
    echo "📝 $1"
}

log_success() {
    echo "✅ $1"
}

log_warning() {
    echo "⚠️  $1" >&2
}

log_error() {
    echo "❌ $1" >&2
}

# =============================================================================
# Bootstrap Functions
# =============================================================================

check_prerequisites() {
    if ! command -v jq &> /dev/null; then
        ERROR_MESSAGES+=("jq not found. Install with: brew install jq (macOS) or sudo apt-get install jq (Linux)")
        log_warning "jq not found - AI labels will be skipped"
        log_warning "Install with: brew install jq (macOS) or sudo apt-get install jq (Linux)"
        return 1  # Non-blocking - handled by caller
    fi
    return 0
}

bootstrap_if_needed() {
    if [ -f "${INSTALLED_MARKER}" ]; then
        return 0
    fi

    echo ""
    log_info "First-time setup: Initializing AI labels system for Backpack Android..."

    # Create config from defaults if missing
    if [ ! -f "${CONFIG_FILE}" ] && [ -f "${CONFIG_DEFAULT}" ]; then
        cp "${CONFIG_DEFAULT}" "${CONFIG_FILE}"
        log_success "Created config.yaml from defaults"
    fi

    # Check prerequisites
    if ! check_prerequisites; then
        log_warning "Setup incomplete - jq required for AI labels"
        log_warning "Tip: For Android development, ensure log file is in repo root, not in app/ or Backpack/ subdirectories"
    else
        log_success "Prerequisites validated"
    fi

    # Mark as installed
    touch "${INSTALLED_MARKER}"
    log_success "AI labels system ready!"
    echo ""

    return 0
}

# =============================================================================
# Label Processing Functions
# =============================================================================

find_log_files() {
    # Always search in git repository root, not current directory
    # This ensures logs are found even if user is working in subdirectories (app/, Backpack/, etc.)
    find "${GIT_ROOT}" -maxdepth 1 -name "${LOG_FILE_PATTERN}" -type f 2>/dev/null || true
}

validate_json_file() {
    local file="$1"

    if ! jq empty "$file" 2>/dev/null; then
        ERROR_MESSAGES+=("Invalid JSON in $(basename "$file")")
        log_warning "Invalid JSON in $(basename "$file") - skipping"
        return 1
    fi

    if ! jq -e '.changes' "$file" &>/dev/null; then
        ERROR_MESSAGES+=("Missing 'changes' array in $(basename "$file")")
        log_warning "Missing 'changes' array in $(basename "$file") - skipping"
        return 1
    fi

    return 0
}

extract_labels_from_file() {
    local file="$1"

    # Extract type_of_change labels
    jq -r '.changes[].type_of_change[]' "$file" 2>/dev/null | grep -v '^$' || true
}

extract_metadata_from_file() {
    local file="$1"
    local field="$2"  # "model" or "tokens_used"

    # Extract all values and sum tokens, or get unique models
    if [ "$field" = "tokens_used" ]; then
        jq -r '[.changes[].tokens_used] | join(",")' "$file" 2>/dev/null || echo "unknown"
    else
        jq -r "[.changes[].${field}] | unique | join(\", \")" "$file" 2>/dev/null || echo "unknown"
    fi
}

merge_unique_labels() {
    sort -u | paste -sd "," -
}

add_label_prefix() {
    local labels="$1"
    # Add "ai: " prefix to each label
    echo "$labels" | tr ',' '\n' | sed "s/^/${LABEL_PREFIX}/" | paste -sd "," -
}

process_log_files() {
    # First argument is commit message file path (from prepare-commit-msg hook)
    local commit_msg_file="${1:-}"

    local log_files
    log_files=$(find_log_files)

    if [ -z "$log_files" ]; then
        # No log files found - silent success
        return 0
    fi

    # Check jq availability
    if ! command -v jq &> /dev/null; then
        log_warning "jq not installed - skipping AI labels"
        return 0
    fi

    log_info "Found AI work logs - processing from multiple tools..."

    # Arrays to collect labels and metadata
    declare -a all_types=()
    declare -a tool_metadata=()  # Store "tool:model" entries
    declare -a tool_names=()     # Store tool names for tool labels

    # Process each log file
    local processed_count=0
    while IFS= read -r log_file; do
        local tool_name
        tool_name=$(basename "$log_file" .json | sed 's/Log\.//')

        # Validate JSON structure
        if ! validate_json_file "$log_file"; then
            continue
        fi

        echo "   Processing: $tool_name"

        # Extract types (use cases)
        while IFS= read -r type; do
            if [ -n "$type" ]; then
                all_types+=("$type")
            fi
        done < <(extract_labels_from_file "$log_file")

        # Extract metadata
        local model
        model=$(extract_metadata_from_file "$log_file" "model")

        # Store tool metadata and name
        tool_metadata+=("$tool_name:$model")
        tool_names+=("$tool_name")

        ((processed_count++))
    done <<< "$log_files"

    # Check if we processed any files
    if [ "$processed_count" -eq 0 ]; then
        log_warning "No valid log files found"
        # Return 1 if there were errors during validation, 0 if just no logs
        [ ${#ERROR_MESSAGES[@]} -gt 0 ] && return 1 || return 0
    fi

    # Get unique labels
    local unique_types
    unique_types=$(printf '%s\n' "${all_types[@]}" | merge_unique_labels)

    # Get unique tool names
    local unique_tools
    unique_tools=$(printf '%s\n' "${tool_names[@]}" | merge_unique_labels)

    # Validate we have labels
    if [ -z "$unique_types" ]; then
        log_warning "No valid labels extracted from log files"
        # Return 1 if there were errors during validation, 0 if just no labels
        [ ${#ERROR_MESSAGES[@]} -gt 0 ] && return 1 || return 0
    fi

    # Add "ai: " prefix to use case labels
    local prefixed_types
    prefixed_types=$(add_label_prefix "$unique_types")

    # Add "ai: " prefix to tool labels
    local prefixed_tools
    prefixed_tools=$(add_label_prefix "$unique_tools")

    log_success "Merged labels from $processed_count tool(s):"
    echo "   Use Cases: $prefixed_types"
    echo "   Tools: $prefixed_tools"

    # Append to commit message (pass commit file path and metadata)
    append_labels_to_commit "$prefixed_types" "$prefixed_tools" "$commit_msg_file" "${tool_metadata[@]}"

    # Clean up log files
    cleanup_log_files "$log_files"

    return 0
}

append_labels_to_commit() {
    local types="$1"
    local tools="$2"
    local commit_msg_file="$3"
    shift 3  # Remaining args are tool_metadata array
    local tool_metadata=("$@")

    # Fallback if not provided (for manual testing)
    if [ -z "$commit_msg_file" ]; then
        commit_msg_file="$(git rev-parse --git-dir)/COMMIT_EDITMSG"
    fi

    if [ ! -f "$commit_msg_file" ]; then
        ERROR_MESSAGES+=("Commit message file not found: $commit_msg_file")
        log_warning "Commit message file not found: $commit_msg_file"
        return 1
    fi

    # Build metadata lines
    local metadata_lines=""
    for entry in "${tool_metadata[@]}"; do
        IFS=':' read -r tool model <<< "$entry"
        metadata_lines+="AI-Tool: ${LABEL_PREFIX}$tool | Model: $model"$'\n'
    done

    # Append labels and metadata to commit message
    {
        echo ""
        echo "AI-Labels: $types"
        echo "AI-Tools: $tools"
        echo "$metadata_lines"
    } >> "$commit_msg_file"

    log_success "Labels appended to commit message"
    return 0
}

cleanup_log_files() {
    local log_files="$1"

    while IFS= read -r log_file; do
        rm -f "$log_file"
    done <<< "$log_files"

    log_success "Cleaned up log files"
}

# =============================================================================
# Main Execution
# =============================================================================

main() {
    # Bootstrap on first run
    bootstrap_if_needed

    # Check prerequisites before processing
    if ! check_prerequisites; then
        # Silently skip AI labels if jq is not installed
        exit 0
    fi

    # Process AI labels (pass commit message file path from hook)
    if ! process_log_files "$@"; then
        # Non-blocking: log errors but don't fail commit
        log_warning "AI label processing encountered errors (commit will continue)"

        # Append error note to commit message if file exists
        local commit_msg_file="${1:-$(git rev-parse --git-dir)/COMMIT_EDITMSG}"
        if [ -f "$commit_msg_file" ]; then
            echo "" >> "$commit_msg_file"
            echo "[AI-Labels: Processing failed]" >> "$commit_msg_file"

            # Append error reasons
            if [ ${#ERROR_MESSAGES[@]} -gt 0 ]; then
                for error in "${ERROR_MESSAGES[@]}"; do
                    echo "  - $error" >> "$commit_msg_file"
                done
            fi
        fi
    fi

    # Always exit successfully (non-blocking)
    exit 0
}

# Run main function
main "$@"
