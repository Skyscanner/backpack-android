#!/usr/bin/env node

const express = require('express');
const fs = require('fs');
const path = require('path');
const { marked } = require('marked');

const app = express();
const PORT = 3000;

// Serve static files (screenshots, etc.)
app.use('/screenshots', express.static(path.join(__dirname, 'docs')));
app.use('/static', express.static(path.join(__dirname, 'docs')));

// Custom CSS for better markdown rendering
const customCSS = `
<style>
  body {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Helvetica, Arial, sans-serif;
    line-height: 1.6;
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
    background-color: #ffffff;
  }
  .dark-mode {
    background-color: #0d1117;
    color: #c9d1d9;
  }
  h1, h2, h3, h4, h5, h6 {
    color: #24292e;
    border-bottom: 1px solid #eaecef;
    padding-bottom: 0.3em;
  }
  .dark-mode h1, .dark-mode h2, .dark-mode h3, .dark-mode h4, .dark-mode h5, .dark-mode h6 {
    color: #f0f6fc;
    border-bottom-color: #30363d;
  }
  table {
    border-collapse: collapse;
    width: 100%;
    margin: 16px 0;
    box-shadow: 0 1px 3px rgba(0,0,0,0.1);
    border-radius: 6px;
    overflow: hidden;
  }
  th, td {
    border: 1px solid #d0d7de;
    padding: 12px 16px;
    text-align: left;
  }
  .dark-mode th, .dark-mode td {
    border-color: #30363d;
  }
  th {
    background-color: #f6f8fa;
    font-weight: 600;
    position: sticky;
    top: 0;
    z-index: 10;
  }
  .dark-mode th {
    background-color: #161b22;
  }
  tbody tr:hover {
    background-color: #f6f8fa;
  }
  .dark-mode tbody tr:hover {
    background-color: #21262d;
  }
  tbody tr:nth-child(even) {
    background-color: #fafbfc;
  }
  .dark-mode tbody tr:nth-child(even) {
    background-color: #0d1117;
  }
  td:first-child {
    font-weight: 500;
    width: 30%;
  }
  td:nth-child(2), td:nth-child(3) {
    width: 35%;
    text-align: center;
  }
  img {
    max-width: 100%;
    height: auto;
    border: 1px solid #d0d7de;
    border-radius: 6px;
    margin: 8px 0;
  }
  .dark-mode img {
    border-color: #30363d;
  }
  pre {
    background-color: #f6f8fa;
    border-radius: 6px;
    padding: 16px;
    overflow: auto;
  }
  .dark-mode pre {
    background-color: #161b22;
  }
  code {
    background-color: rgba(175,184,193,0.2);
    padding: 0.2em 0.4em;
    border-radius: 3px;
    font-size: 85%;
  }
  .dark-mode code {
    background-color: rgba(110,118,129,0.4);
  }
  .nav {
    background-color: #f6f8fa;
    padding: 10px;
    border-radius: 6px;
    margin-bottom: 20px;
  }
  .dark-mode .nav {
    background-color: #161b22;
  }
  .nav a {
    margin-right: 15px;
    text-decoration: none;
    color: #0969da;
  }
  .dark-mode .nav a {
    color: #58a6ff;
  }
  .nav a:hover {
    text-decoration: underline;
  }
  .toggle-theme {
    position: fixed;
    top: 20px;
    right: 20px;
    padding: 8px 12px;
    background-color: #0969da;
    color: white;
    border: none;
    border-radius: 6px;
    cursor: pointer;
  }
  .dark-mode .toggle-theme {
    background-color: #58a6ff;
    color: #0d1117;
  }
</style>
`;

const themeToggleScript = `
<script>
  function toggleTheme() {
    document.body.classList.toggle('dark-mode');
    const theme = document.body.classList.contains('dark-mode') ? 'dark' : 'light';
    localStorage.setItem('theme', theme);
  }
  
  // Load saved theme
  const savedTheme = localStorage.getItem('theme');
  if (savedTheme === 'dark') {
    document.body.classList.add('dark-mode');
  }
</script>
`;

// Function to convert GitHub raw URLs to local paths
function convertImageUrls(markdown, currentPath) {
  return markdown.replace(
    /https:\/\/raw\.githubusercontent\.com\/Skyscanner\/backpack-android\/main\/docs\/(.*?)\/(.*?)\/(screenshots\/.*?\.png)/g,
    (match, component, subpath, screenshotPath) => {
      return `/screenshots/${component}/${subpath}/${screenshotPath}`;
    }
  );
}

// Function to scan for all README files
function findReadmeFiles(dir, basePath = '') {
  const files = [];
  const items = fs.readdirSync(dir);
  
  for (const item of items) {
    const fullPath = path.join(dir, item);
    const stat = fs.statSync(fullPath);
    
    if (stat.isDirectory()) {
      files.push(...findReadmeFiles(fullPath, path.join(basePath, item)));
    } else if (item === 'README.md') {
      files.push({
        path: path.join(basePath, item),
        fullPath: fullPath,
        component: path.basename(path.dirname(fullPath))
      });
    }
  }
  
  return files;
}

// Home page with component list
app.get('/', (req, res) => {
  const docsPath = path.join(__dirname, 'docs');
  const composeReadmes = findReadmeFiles(path.join(docsPath, 'compose'), 'compose');
  const viewReadmes = findReadmeFiles(path.join(docsPath, 'view'), 'view');
  
  // Create a map of all components with their available documentation types
  const componentMap = new Map();
  
  // Add compose components
  composeReadmes.forEach(readme => {
    const urlPath = readme.path.replace(/\\/g, '/');
    componentMap.set(readme.component, {
      name: readme.component,
      compose: `/docs/${urlPath}`,
      view: null
    });
  });
  
  // Add view components
  viewReadmes.forEach(readme => {
    const urlPath = readme.path.replace(/\\/g, '/');
    if (componentMap.has(readme.component)) {
      componentMap.get(readme.component).view = `/docs/${urlPath}`;
    } else {
      componentMap.set(readme.component, {
        name: readme.component,
        compose: null,
        view: `/docs/${urlPath}`
      });
    }
  });
  
  // Sort components alphabetically
  const sortedComponents = Array.from(componentMap.values()).sort((a, b) => 
    a.name.localeCompare(b.name)
  );
  
  let html = `
    <!DOCTYPE html>
    <html>
    <head>
        <title>Backpack Android Documentation Preview</title>
        <meta charset="utf-8">
        ${customCSS}
    </head>
    <body>
        <button class="toggle-theme" onclick="toggleTheme()">🌓 Toggle Theme</button>
        <h1>Backpack Android Documentation Preview</h1>
        
        <p><strong>Instructions:</strong> Click on any link below to preview component documentation. Images will be loaded from your local repository.</p>
        
        <table>
          <thead>
            <tr>
              <th>Component Name</th>
              <th>Compose Documentation</th>
              <th>View Documentation</th>
            </tr>
          </thead>
          <tbody>
  `;
  
  sortedComponents.forEach(component => {
    const composeLink = component.compose 
      ? `<a href="${component.compose}">📱 View Compose Docs</a>` 
      : '<span style="color: #6a737d;">Not Available</span>';
    
    const viewLink = component.view 
      ? `<a href="${component.view}">📄 View Legacy Docs</a>` 
      : '<span style="color: #6a737d;">Not Available</span>';
    
    html += `
            <tr>
              <td><strong>${component.name}</strong></td>
              <td>${composeLink}</td>
              <td>${viewLink}</td>
            </tr>
    `;
  });
  
  html += `
          </tbody>
        </table>
        
        <p><em>Total Components: ${sortedComponents.length} | Compose: ${composeReadmes.length} | View: ${viewReadmes.length}</em></p>
        
        ${themeToggleScript}
    </body>
    </html>
  `;
  
  res.send(html);
});

// Serve markdown files
app.get('/docs/*', (req, res) => {
  const requestedPath = req.params[0];
  const filePath = path.join(__dirname, 'docs', requestedPath);
  
  if (!fs.existsSync(filePath)) {
    return res.status(404).send('File not found');
  }
  
  try {
    let markdown = fs.readFileSync(filePath, 'utf8');
    
    // Convert GitHub raw URLs to local paths
    markdown = convertImageUrls(markdown, requestedPath);
    
    const html = marked.parse(markdown);
    const componentName = path.basename(path.dirname(filePath));
    
    const fullHtml = `
      <!DOCTYPE html>
      <html>
      <head>
          <title>${componentName} - Backpack Android</title>
          <meta charset="utf-8">
          ${customCSS}
      </head>
      <body>
          <button class="toggle-theme" onclick="toggleTheme()">🌓 Toggle Theme</button>
          <div class="nav">
              <a href="/">← Back to Components</a>
              <strong>Current: ${componentName}</strong>
          </div>
          ${html}
          ${themeToggleScript}
      </body>
      </html>
    `;
    
    res.send(fullHtml);
  } catch (error) {
    res.status(500).send('Error reading file: ' + error.message);
  }
});

app.listen(PORT, () => {
  console.log(`📚 Backpack Documentation Preview Server running at:`);
  console.log(`🌐 http://localhost:${PORT}`);
  console.log(`\n📁 Serving docs from: ${path.join(__dirname, 'docs')}`);
  console.log(`🖼️  Screenshots served from local files`);
  console.log(`\n🚀 Open your browser and navigate to the URL above to start browsing!`);
});