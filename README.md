# UWO SpellChecker

A C-based spell checker developed as part of a university-level systems programming course. This project demonstrates core C concepts such as memory management, file I/O, string manipulation, and hash tables for efficient word lookup.

## 📚 Overview

The SpellChecker reads a text file and checks each word against a dictionary. Words not found in the dictionary are flagged as potential spelling errors and displayed to the user.

### Key Features
- Fast dictionary loading using a hash table
- Custom hash function
- Modular C code for readability and maintainability
- Handles punctuation, capitalization, and numerical data

## 📁 Project Structure

.
├── dictionary.c # Dictionary loading and word-checking logic
├── dictionary.h # Header for dictionary.c
├── spell.c # Main spell-checking program
├── Makefile # Compile instructions
├── README.md # Project documentation
└── tests/ # Sample dictionary and input files
