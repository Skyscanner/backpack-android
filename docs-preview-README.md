# Documentation Preview Server

A simple local server to preview Backpack Android documentation with proper image rendering.

## What it does

- 🌐 **Local Preview**: Serves your markdown documentation files in a web browser
- 🖼️ **Image Support**: Automatically converts GitHub raw URLs to local file paths so you can see screenshots from your local repo
- 🎨 **GitHub-style Rendering**: Clean, GitHub-flavored markdown rendering with syntax highlighting
- 🌓 **Dark/Light Mode**: Toggle between themes for comfortable viewing
- 📱 **Component Browser**: Easy navigation between all Compose and View components

## Quick Start

1. **Install dependencies** (first time only):
   ```bash
   npm install
   ```

2. **Start the preview server**:
   ```bash
   npm run preview-docs
   ```

3. **Open your browser** to:
   ```
   http://localhost:3000
   ```

4. **Browse your docs**: Click on any component name to preview its documentation

## Features

### 🔄 Auto Image Path Conversion
The server automatically converts URLs like:
```
https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Button/screenshots/default.png
```
to local paths that work with your repository files.

### 🎯 Live Changes
Since the server reads files directly from your local repository:
- Edit any README.md file
- Refresh the browser page
- See your changes immediately!

### 🗂️ Easy Navigation
- Home page lists all available components
- Navigate between Compose and View components
- Back buttons for easy browsing

## File Structure

The server looks for README.md files in:
- `docs/compose/*/README.md` - Compose components
- `docs/view/*/README.md` - View components

Screenshots are served from:
- `docs/compose/*/screenshots/*.png`
- `docs/view/*/screenshots/*.png`

## Stopping the Server

Press `Ctrl+C` in the terminal where the server is running.

## Use Cases

Perfect for:
- 📝 **Previewing README changes** before committing
- 🖼️ **Checking image links** work correctly
- 📖 **Reviewing documentation** during development
- 👀 **Sharing local docs** with team members
- 🧪 **Testing markdown formatting** and styling

## Troubleshooting

**Port already in use?** 
- The server uses port 3000 by default
- If it's occupied, kill other processes using that port or modify the PORT in `preview-server.js`

**Images not loading?**
- Make sure your screenshot files exist in the expected locations
- Check that the markdown uses the GitHub raw URL format for automatic conversion

**README not found?**
- Ensure your README.md files are in the expected directory structure
- Check file permissions