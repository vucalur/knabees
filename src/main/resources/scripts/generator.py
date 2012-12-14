#!/usr/bin/env python

import random
from sys import argv, stdout
from functools import reduce

def usage():
    print("Usage: python " + argv[0] + " dimensions items_num knapsack_max items_max")
    exit()
    
def print_list(l):
    print reduce(lambda x,y: str(x)+" "+str(y), l)
    
try:
    dimensions = int(argv[1])
    items_num = int(argv[2])
    knapsack_max = int(argv[3])
    items_max = int(argv[4])
except Exception:
    usage()


knapsack = [knapsack_max for x in range(dimensions)]
items = [[random.randrange(1,items_max) for x in range(dimensions+1)]
		for y in range(items_num)]
    
print(str(dimensions)+" "+str(items_num))
print_list(knapsack)
for item in items:
	print_list(item)
