import random
from sys import argv, stdout
from functools import reduce

def usage():
    print("Usage:")
    print("python generator.py dimensions items maximum")
    exit()
    
def fprint(s):
    print(s)
    
try:
    dimensions = int(argv[1])
    items = int(argv[2])
    maximum = int(argv[3])
except Exception:
    usage()
    
print(str(dimensions)+" "+str(items))

print(
    reduce(
        lambda x, y: str(x)+" "+str(y),
        map(
            lambda x: random.random() * maximum,
            range(dimensions)
        )
    )
)


list(map(
    lambda v: fprint(
                reduce(
                    lambda x, y: str(x)+" "+str(y),
                    map(
                        lambda z: random.random() * maximum,
                        range(dimensions + 1)
                    )
                )
            ),
    range(items)
))
