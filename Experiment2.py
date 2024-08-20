# Process the results of Experiment 2 and visualize them
import pandas as pd
import matplotlib.pyplot as plt

# Read the results of experiment 2, The absolute path needs to be changed during testing
df = pd.read_csv('/Users/yzgxy96/IdeaProjects/MyProject/Experiment2_Results.csv')

actual_comparisons_mean = df['Actual Comparisons'].mean()
theoretical_comparisons_mean = df['Theoretical Comparisons'].mean()

print(f"Average Actual Comparisons: {actual_comparisons_mean:.2f}")
print(f"Average Theoretical Comparisons: {theoretical_comparisons_mean:.2f}")

# How actual performance and theoretical value change
# as the number of stocks increases
plt.figure(figsize=(10, 6))
plt.plot(df['Stock Count'], df['Actual Comparisons'], label='Actual Comparisons', marker='o')
plt.plot(df['Stock Count'], df['Theoretical Comparisons'], label='Theoretical Comparisons', marker='x')

plt.xlabel('Number of Stocks')
plt.ylabel('Number of Comparisons')

plt.legend()
plt.savefig('Experiment2_Figure1.png')
plt.show()

# How the running time changes as the number of stocks increases
plt.figure(figsize=(10, 6))
plt.plot(df['Stock Count'], df['Time (ms)'], label='Running Time (ms)', marker='o')

plt.xlabel('Number of Stocks')
plt.ylabel('Running Time (ms)')
plt.legend()
plt.savefig('Experiment2_Figure2.png')
plt.show()