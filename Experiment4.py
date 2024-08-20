# Process the results of Experiment 4 and visualize them
import pandas as pd
import matplotlib.pyplot as plt

# Read the results of experiment 4, The absolute path needs to be changed during testing
df = pd.read_csv('/Users/yzgxy96/IdeaProjects/MyProject/Experiment4_Results.csv')

plt.figure(figsize=(10, 6))
plt.plot(df['Stock Count'], df['Time (ms)'], label='Running Time (ms)', marker='o', markersize=1)

plt.xlabel('Number of Stocks')
plt.ylabel('Running Time (ms)')
plt.legend()
plt.savefig('Experiment4_Figure1.png')
plt.show()
