import re,json,sys
data = []

if len(sys.argv) < 3:
    print("Pass input and output file names")
    exit()
    
inFile = sys.argv[1]
outFile = sys.argv[2]
with open(inFile) as f:
  lines = f.readlines()

for line in lines:
    items = re.split(r'\t+', line)
    if (len(items) == 3):
        node = {'id':items[0],'title':items[1],'body':items[2]}
        data.append(node)
    else:
        node = {'id':items[0],'body':items[1]}
        data.append(node)

with open(outFile, 'w') as f:
    json.dump(data, f)