#include <bits/stdc++.h>
#include <stdio.h>
#include <dirent.h>
#include <sys/types.h>

using namespace std;
const string BASE_DIR = "./";
const string LEVEL_DIR = BASE_DIR + "level/";
const string ASTAR_DIR = BASE_DIR + "astar/";
const string ASTAR_RES_DIR = BASE_DIR + "astar_result/";
const string PROMPTS_DIR = BASE_DIR + "prompts/";

int L = 0, R = 80;
void solvePrompt(string dir) {
    for (int i = L; i <= R; i++) {
        string filename = to_string(i) + ".txt";
        string filepath = dir + filename;
        cout << filepath << endl;
        
    }
}

int main(int argc, char** argv) { 
    solvePrompt(PROMPTS_DIR);
}

