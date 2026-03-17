$ErrorActionPreference = "Stop"

function Translate-Text([string]$text, [string]$lang, [hashtable]$cache) {
    if ([string]::IsNullOrWhiteSpace($text)) { return $text }
    if ($cache.ContainsKey($text)) { return $cache[$text] }

    $encoded = [uri]::EscapeDataString($text)
    $url = "https://api.mymemory.translated.net/get?q=$encoded&langpair=en%7C$lang"
    $translated = $text

    try {
        $resp = Invoke-RestMethod -Uri $url -Method Get -TimeoutSec 30
        if ($resp.responseData.translatedText) {
            $translated = [string]$resp.responseData.translatedText
        }
    } catch {
        $translated = $text
    }

    $translated = $translated -replace '\[ترجمة المصطلح:\s*', ''
    $translated = $translated -replace '\]$', ''
    $cache[$text] = $translated
    Start-Sleep -Milliseconds 120
    return $translated
}

$themes = @("math", "physics", "chemistry", "jurisprudence", "astronomy")
$langs = @("ru", "ar")

foreach ($lang in $langs) {
    $cache = @{}
    foreach ($theme in $themes) {
        $sourcePath = "composeApp/src/androidMain/assets/quiz/en/$theme.json"
        $targetPath = "composeApp/src/androidMain/assets/quiz/$lang/$theme.json"

        $source = Get-Content $sourcePath -Raw | ConvertFrom-Json

        if ($lang -eq "ru") {
            switch ($theme) {
                "math" { $source.theme_name = "Математика" }
                "physics" { $source.theme_name = "Физика" }
                "chemistry" { $source.theme_name = "Химия" }
                "jurisprudence" { $source.theme_name = "Правоведение" }
                "astronomy" { $source.theme_name = "Астрономия" }
            }
        } else {
            switch ($theme) {
                "math" { $source.theme_name = "الرياضيات" }
                "physics" { $source.theme_name = "الفيزياء" }
                "chemistry" { $source.theme_name = "الكيمياء" }
                "jurisprudence" { $source.theme_name = "الفقه" }
                "astronomy" { $source.theme_name = "الفلك" }
            }
        }

        foreach ($q in $source.questions) {
            $optionMap = @{}
            foreach ($k in @("answer_1", "answer_2", "answer_3", "answer_4")) {
                $old = [string]$q.$k
                $new = Translate-Text $old $lang $cache
                $q.$k = $new
                $optionMap[$old] = $new
            }

            $q.answer = Translate-Text ([string]$q.answer) $lang $cache

            $oldRight = [string]$q.right_answer
            if ($optionMap.ContainsKey($oldRight)) {
                $q.right_answer = $optionMap[$oldRight]
            } else {
                $q.right_answer = Translate-Text $oldRight $lang $cache
            }
        }

        $source | ConvertTo-Json -Depth 12 | Set-Content -Path $targetPath -Encoding utf8
    }

    Copy-Item "composeApp/src/androidMain/assets/quiz/$lang/*.json" "composeApp/src/commonMain/composeResources/files/quiz/$lang/" -Force
    Write-Output "rebuilt $lang uniqueStrings=$($cache.Count)"
}
Write-Output "Rebuild localization complete"
