# Process the results of Experiment 5 and visualize them
import pandas as pd
import matplotlib.pyplot as plt

# Read the results of experiment 5, The absolute path needs to be changed during testing
df = pd.read_csv('/Users/yzgxy96/IdeaProjects/MyProject/Experiment5_Results.csv')

plt.figure(figsize=(10, 6))
plt.scatter(df['Estimated Theoretical Comparisons'], df['Actual Comparisons'], label='Data Points', color='blue', s=20)

# y=x
min_val = min(df['Estimated Theoretical Comparisons'].min(), df['Actual Comparisons'].min())
max_val = max(df['Estimated Theoretical Comparisons'].max(), df['Actual Comparisons'].max())
plt.plot([min_val, max_val], [min_val, max_val], 'r--', label='y = x')

plt.xlabel('Theoretical Comparisons')
plt.ylabel('Actual Comparisons')

plt.legend()
plt.savefig('Experiment5_Figure1.png')
plt.show()

# Calculate and output the average of the actual number of comparisons
# and the theoretical number of comparisons
average_theoretical_comparisons = df['Estimated Theoretical Comparisons'].mean()
average_actual_comparisons = df['Actual Comparisons'].mean()

print(f"Average Theoretical Comparisons: {average_theoretical_comparisons:.2f}")
print(f"Average Actual Comparisons: {average_actual_comparisons:.2f}")
