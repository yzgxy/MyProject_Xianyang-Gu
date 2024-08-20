# Process the results of Experiment 10 and visualize them

import pandas as pd
import matplotlib.pyplot as plt

# Read the results of experiment 10, The absolute path needs to be changed during testing
df = pd.read_csv('/Users/yzgxy96/IdeaProjects/MyProject/Experiment10_Results.csv')

# theoretical performance
plt.plot(df['File Count'], df['Estimated Theoretical Comparisons'], label='Theoretical Comparisons', color='blue')

# actual performance
plt.plot(df['File Count'], df['Actual Comparisons'], label='Actual Comparisons', color='orange')

plt.xlabel('Number of Stocks')
plt.ylabel('Comparisons')
plt.legend()
plt.savefig('Experiment10_Figure.png')
plt.show()
