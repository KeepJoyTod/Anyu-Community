Param(
    [string]$Path = 'd:\javaStudy\项目二\整合\Behind\ruoyi-notice\src\main\resources\mapper\notice\CommunityyNoticeMapper.xml'
)

if (!(Test-Path $Path)) {
    Write-Error "File not found: $Path"
    exit 1
}

Copy-Item $Path ("$Path.bak") -Force

$content = Get-Content -Raw -Encoding UTF8 $Path

# Fix SELECT list to avoid non-existent columns and alias to match resultMap
$content = $content -replace 'select id, notice_title, notice_content, notice_type, publish_user_id, publish_user_name, publish_time, status, top_flag, top_end_time, read_count, create_by, create_time, update_by, update_time, remark from community_notice', 'select notice_id as id, notice_title, notice_content, notice_type, publisher as publish_user_name, publish_time, status, is_top as top_flag, create_by, create_time, update_by, update_time, remark from community_notice'

# Remove filter on non-existent publish_user_id
$content = [regex]::Replace(
    $content,
    '^[\s\t]*<if test="publishUserId != null ">\s*and publish_user_id = #\{publishUserId\}\s*</if>\s*\r?\n',
    '',
    [System.Text.RegularExpressions.RegexOptions]::Multiline
)

# Change publish_user_name filter to actual column publisher
$content = $content -replace 'publish_user_name like', 'publisher like'

# Change top_flag filter to actual column is_top
$content = $content -replace 'top_flag =', 'is_top ='

# Remove filter on non-existent top_end_time
$content = [regex]::Replace(
    $content,
    '^[\s\t]*<if test="topEndTime != null ">\s*and DATE\(top_end_time\) = DATE\(#\{topEndTime\}\)\s*</if>\s*\r?\n',
    '',
    [System.Text.RegularExpressions.RegexOptions]::Multiline
)

# Remove filter on non-existent read_count
$content = [regex]::Replace(
    $content,
    '^[\s\t]*<if test="readCount != null ">\s*and read_count = #\{readCount\}\s*</if>\s*\r?\n',
    '',
    [System.Text.RegularExpressions.RegexOptions]::Multiline
)

# Fix where clause for select-by-id
$content = $content -replace 'where id = #\{id\}', 'where notice_id = #{id}'

Set-Content -Path $Path -Value $content -Encoding UTF8
Write-Host "Patched: $Path"
