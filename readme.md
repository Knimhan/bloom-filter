# Bloom Filters

### Problem statement mentioned here:
http://codekata.com/kata/kata05-bloom-filters/


### Solution Approach

This solution follows following steps
  1. Uses MD5 hash function which generates fairly large long hash.
  2. Extracts sequence of bit which are randomly selected to get a hash value. 
  3. Uses BitSet to make these bits to true.  
Follow these steps to add and to check if word contains int the dictionary. 


#### BloomFilter 
  1. void add(List<String> words) : 

Adds list of words to bit set. By creating 'n' no of hashes and extracting random bits to set in a bit set.
  
  2. boolean contains(String word)

Check if the word is present in bit set. By creating 'n' no of hashes and extracting random bits to check if these are true in a bit set.

#### WordChecker
  1. List<String> readWordList(String filename) : 
  
Reads the file mentioned in the input parameter and converts it to List<String>.
