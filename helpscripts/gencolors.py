#Take all files in the 'in' folder and replace with all the colors in 'out' folder
#It's to make recipes and stuff
import glob
import os

REPLACE = '$temp$'
COLORS = ['black', 'blue', 'brown', 'cyan', 'gray', 'green', 'light_blue', 'light_gray', 'lime', 'magenta', 'orange', 'pink', 'purple', 'red', 'white', 'yellow']
files = glob.glob('.\\in\\*')
for f in files:
	with open(f, 'r') as template:
		name = '.\\out\\' + os.path.basename(f)
		content = template.read()
		for c in COLORS:
			with open(name.replace(REPLACE, c), 'w') as target:
				target.write(content.replace(REPLACE, c))
print('Done!')