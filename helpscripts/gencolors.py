#Take all files in the 'in' folder and replace with all the colors in 'out' folder
#It's to make recipes and stuff
import glob
import os

REPLACE = '$temp$'
#Vanilla
COLORS = ['black', 'blue', 'brown', 'cyan', 'gray', 'green', 'light_blue', 'light_gray', 'lime', 'magenta', 'orange', 'pink', 'purple', 'red', 'white', 'yellow']
#Modded dyes for future reference
#Currently not doing it because:
#-have to register block and hide it (since loot tables can't be conditional)
#-can't optionally put in the tag (and JEI picks it up in recipes and I don't want the unobtainable colors to appear)
#Mekanism
#COLORS = ['dark_red', 'aqua']
#Dyenamics
#COLORS = ['peach', 'aquamarine', 'fluorescent', 'mint', 'maroon', 'bubblegum', 'lavender', 'persimmon', 'cherenkov']
files = glob.glob('.\\in\\*')
for f in files:
	with open(f, 'r') as template:
		name = '.\\out\\' + os.path.basename(f)
		content = template.read()
		for c in COLORS:
			with open(name.replace(REPLACE, c), 'w') as target:
				target.write(content.replace(REPLACE, c))
print('Done!')