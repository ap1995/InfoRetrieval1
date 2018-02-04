import json,sys

if len(sys.argv) < 3:
    print("Pass input filename and no of files")
    exit()
inFile = sys.argv[1]
noOfFiles = int(sys.argv[2])
with open(inFile) as f:
  data = json.load(f)

total = len(data)
division = total // noOfFiles
start = 0
count = 1
while(count <= noOfFiles):
    # split_data = []
    if(count == noOfFiles):
        split_data = data[start:]
    else:
        split_data = data[start:start+division]
    outFile = "split_data_"+str(count)+".json"
    print(len(split_data))
    with open(outFile, 'w') as f:
        json.dump(split_data, f)
    count += 1
    start += division

