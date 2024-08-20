# Process the results of Experiment 1 and visualize them
import pandas as pd
import matplotlib.pyplot as plt

# Read the results of experiment 1, The absolute path needs to be changed during testing
df = pd.read_csv('/Users/yzgxy96/IdeaProjects/MyProject/Experiment1_Results.csv')

plt.figure(figsize=(10, 6))
plt.scatter(df['Theoretical Comparisons'], df['Actual Comparisons'], c='blue', s=5, label='Data Points')

min_val = min(df['Theoretical Comparisons'].min(), df['Actual Comparisons'].min())
max_val = max(df['Theoretical Comparisons'].max(), df['Actual Comparisons'].max())
plt.plot([min_val, max_val], [min_val, max_val], 'r--', label='y = x')

plt.xlabel('Theoretical Comparisons')
plt.ylabel('Actual Comparisons')

plt.legend()
plt.savefig('Experiment1_Results.png')
plt.show()

# calculate average numbers
actual_comparisons_mean = df['Actual Comparisons'].mean()
theoretical_comparisons_mean = df['Theoretical Comparisons'].mean()
print(f"Average Actual Comparisons: {actual_comparisons_mean:.2f}")
print(f"Average Theoretical Comparisons: {theoretical_comparisons_mean:.2f}")
