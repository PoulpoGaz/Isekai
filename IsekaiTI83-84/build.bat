@echo off

make debug && (
	echo Launching CEmu
	CEmu --send "bin/ISEKAI.8xp" --launch "ISEKAI"
) || (
	echo Build failed
)