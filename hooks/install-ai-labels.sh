#!/bin/bash
# install-ai-labels.sh
# Installs ai-labels for automatic AI tool detection in git commits
# This script is designed to fail gracefully - it will never block your workflow

set -o pipefail

warn() {
    echo -e "⚠️  Warning: $1 >&2"
}

success() {
    echo -e "✓ $1"
}

info() {
    echo "$1"
}

# Check if ai-labels is already installed
if command -v ai-labels &> /dev/null; then
    HOOK_FILE="$(git rev-parse --git-dir 2>/dev/null)/hooks/prepare-commit-msg"
    if [ -f "$HOOK_FILE" ] && grep -q "ai-labels" "$HOOK_FILE" 2>/dev/null; then
        exit 0
    fi
    if ai-labels setup &>/dev/null; then
        success "ai-labels hooks configured for this repository"
    else
        warn "Failed to configure ai-labels hooks, but this won't affect your workflow"
    fi
    exit 0
fi

if ! command -v brew &> /dev/null; then
    warn "Homebrew is not installed. ai-labels requires Homebrew for installation."
    exit 0
fi

info "Installing ai-labels..."

if ! brew tap | grep -q "skyscanner/mshell"; then
    if ! brew tap skyscanner/mshell git@github.com:skyscanner/homebrew-mshell.git --full --force-auto-update 2>/dev/null; then
        warn "Failed to add skyscanner/mshell tap. Ensure you have SSH access to github.com configured."
        exit 0
    fi
fi

if ! brew install ai-labels 2>/dev/null; then
    warn "Failed to install ai-labels via Homebrew"
    exit 0
fi

success "ai-labels installed successfully"

# Run setup to configure hooks
if ai-labels setup 2>/dev/null; then
    success "ai-labels hooks configured"
    info ""
    info "ai-labels will now automatically detect AI tool usage in your commits."
    info "For more information, see: https://github.com/skyscanner/ai-labels"
else
    warn "Failed to configure ai-labels hooks"
    warn "You can manually run 'ai-labels setup' later."
fi
exit 0
