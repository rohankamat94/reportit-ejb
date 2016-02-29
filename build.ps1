$root=Get-Location
$build=$root.ToString()+"\build\classes"
$source=$root.ToString()+"\ejbModule"

$jarName='cirs-ejb.jar'
$sourceJarName='cirs-ejb-source.jar'

$buildFiles=dir .\build\ -Recurse *.class | where {$_.FullName -notmatch 'impl'}
$sourceFiles=dir .\ejbModule\ -Recurse *.java | where {$_.FullName -notmatch 'impl'}

$buildDest="..\CIRSWeb\WebContent\WEB-INF\lib\$jarName"
$sourceDest="..\CIRSWeb\WebContent\WEB-INF\sources\$sourceJarName"

if(Test-Path manifest.txt) {del manifest.txt}
ni -ItemType File manifest.txt

jar cmf manifest.txt $jarName
jar cmf manifest.txt $sourceJarName

foreach($file in $buildFiles){
    jar uvf $jarName -C .\build\classes $file.FullName.Replace($build,'.')
}
foreach($s in $sourceFiles){
    jar uvf $sourceJarName -C .\ejbModule $s.FullName.Replace($source,'.')
}

if(Test-Path $buildDest) {Remove-Item $buildDest}
if(Test-Path $sourceDest) {Remove-Item $sourceDest}

cp $jarName $buildDest -Verbose
cp $sourceJarName $sourceDest -Verbose

Remove-Item jartmp*.tmp
Remove-Item .\manifest.txt
Remove-Item $jarName
Remove-Item $sourceJarName
