# Process the results of Experiment 3 and visualize them
import pandas as pd
import matplotlib.pyplot as plt

# Read the results of experiment 3, The absolute path needs to be changed during testing
df = pd.read_csv('/Users/yzgxy96/IdeaProjects/MyProject/Experiment3_Results.csv')

# Calculate and output the average of the actual number of comparisons
# and the theoretical number of comparisons
actual_comparisons_mean = df['Actual Comparisons'].mean()
theoretical_comparisons_mean = df['Theoretical Comparisons'].mean()

print(f"Average Actual Comparisons: {actual_comparisons_mean:.2f}")
print(f"Average Theoretical Comparisons: {theoretical_comparisons_mean:.2f}")

plt.figure(figsize=(10, 6))
plt.scatter(df['Theoretical Comparisons'], df['Actual Comparisons'], label='Data Points', color='blue', s=5)

# Draw auxiliary lines for y=x
min_val = min(df['Theoretical Comparisons'].min(), df['Actual Comparisons'].min())
max_val = max(df['Theoretical Comparisons'].max(), df['Actual Comparisons'].max())
plt.plot([min_val, max_val], [min_val, max_val], 'r--', label='y = x')

plt.xlabel('Theoretical Comparisons')
plt.ylabel('Actual Comparisons')
plt.legend()
plt.savefig('Experiment3_Figure1.png')
plt.show()




