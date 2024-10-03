# Java GitHub Repository Analyzer

## Overview

This application parses Java GitHub repositories and provides an interactive file explorer that offers insights into the structure and health of the code. It is designed to help developers identify potential issues and improve code quality by analyzing classes and methods within a project.

## Features

- **File Explorer**: Displays a list of Java classes on the left and corresponding methods on the right for each class.
- **Code Health Color Coding**: Methods are color-coded based on their "health" status:
  - **Green**: Healthy code with minimal issues.
  - **Yellow**: Code that could use some improvements.
  - **Red**: Code with significant issues or potential maintenance concerns.
- **Conditional Flagging**: Methods with excessive conditional statements are flagged for review, helping to highlight complexity that may need refactoring.
- **Metrics Used**:
  - Number of lines per method
  - Conditional complexity
  - Other health indicators

## How It Works

1. **GitHub Cloning**: The application clones a Java GitHub repository using JGit.
2. **Code Parsing**: It analyzes the repository’s structure, identifying classes and methods.
3. **Health Analysis**: For each method, the program checks lines of code and conditional density, assigns a color code based on these metrics, and flags complex methods for further attention.
