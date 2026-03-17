import json
import time
from pathlib import Path

from deep_translator import GoogleTranslator

ROOT = Path(__file__).resolve().parents[1]
EN_DIR = ROOT / "composeApp" / "src" / "androidMain" / "assets" / "quiz" / "en"
ANDROID_DIR = ROOT / "composeApp" / "src" / "androidMain" / "assets" / "quiz"
COMMON_DIR = ROOT / "composeApp" / "src" / "commonMain" / "composeResources" / "files" / "quiz"

THEMES = ["math", "physics", "chemistry", "jurisprudence", "astronomy"]
LANGS = ["ru", "ar"]
THEME_NAMES = {
    "ru": {
        "math": "Математика",
        "physics": "Физика",
        "chemistry": "Химия",
        "jurisprudence": "Правоведение",
        "astronomy": "Астрономия",
    },
    "ar": {
        "math": "الرياضيات",
        "physics": "الفيزياء",
        "chemistry": "الكيمياء",
        "jurisprudence": "الفقه",
        "astronomy": "الفلك",
    },
}


def translate_cached(text: str, lang: str, cache: dict[str, str], translators: dict[str, GoogleTranslator]) -> str:
    if not text:
        return text
    if text in cache:
        return cache[text]

    translator = translators[lang]
    translated = text
    for _ in range(3):
        try:
            translated = translator.translate(text)
            break
        except Exception:
            time.sleep(0.4)
            translated = text

    translated = (translated or text).replace("[ترجمة المصطلح:", "").rstrip("]").strip()
    if not translated:
        translated = text

    cache[text] = translated
    time.sleep(0.05)
    return translated


def process_lang(lang: str) -> None:
    cache: dict[str, str] = {}
    translators = {
        "ru": GoogleTranslator(source="en", target="ru"),
        "ar": GoogleTranslator(source="en", target="ar"),
    }

    for theme in THEMES:
        src_path = EN_DIR / f"{theme}.json"
        with src_path.open("r", encoding="utf-8") as f:
            data = json.load(f)

        data["theme_name"] = THEME_NAMES[lang][theme]

        for q in data["questions"]:
            option_map: dict[str, str] = {}
            for key in ["answer_1", "answer_2", "answer_3", "answer_4"]:
                old = str(q.get(key, ""))
                new = translate_cached(old, lang, cache, translators)
                q[key] = new
                option_map[old] = new

            q["answer"] = translate_cached(str(q.get("answer", "")), lang, cache, translators)

            old_right = str(q.get("right_answer", ""))
            q["right_answer"] = option_map.get(old_right, translate_cached(old_right, lang, cache, translators))

        out_path = ANDROID_DIR / lang / f"{theme}.json"
        out_path.parent.mkdir(parents=True, exist_ok=True)
        with out_path.open("w", encoding="utf-8") as f:
            json.dump(data, f, ensure_ascii=False, indent=4)

    src_glob = ANDROID_DIR / lang
    dst_dir = COMMON_DIR / lang
    dst_dir.mkdir(parents=True, exist_ok=True)
    for src_file in src_glob.glob("*.json"):
        dst_file = dst_dir / src_file.name
        dst_file.write_text(src_file.read_text(encoding="utf-8"), encoding="utf-8")

    print(f"localized {lang} unique_strings={len(cache)}")


def main() -> None:
    for lang in LANGS:
        process_lang(lang)
    print("Localization complete")


if __name__ == "__main__":
    main()
