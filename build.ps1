$root=Get-Location
$build=$root.ToString()+"\build\classes"
$javaFiles=dir .\build\ -Recurse *.class | where {$_.FullName -notmatch 'impl'}
ni -ItemType File manifest.txt
jar cmf manifest.txt cirs-ejb.jar 
foreach($jfile in $javaFiles){
    jar uvf cirs-ejb.jar -C .\build\classes $jfile.FullName.Replace($build,'.')
}

cp cirs-ejb.jar ..\CIRSWeb\WebContent\WEB-INF\lib\cirs-ejb.jar
Remove-Item jartmp*.tmp
Remove-Item .\manifest.txt
Remove-Item .\cirs-ejb.jar