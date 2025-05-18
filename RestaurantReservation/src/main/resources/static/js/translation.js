document.addEventListener("DOMContentLoaded", () => {
    setupTranslationLoader();
});

function setupTranslationLoader() {
    const defaultLang = localStorage.getItem("lang") || "en";
    loadTranslations(defaultLang);

    const langSelector = document.getElementById("lang-selector");
    if (langSelector) {
        langSelector.value = defaultLang; // Set the dropdown to the saved language
        langSelector.addEventListener("change", (event) => {
            const selectedLang = event.target.value;
            localStorage.setItem("lang", selectedLang);
            loadTranslations(selectedLang);
        });
    }
}

function loadTranslations(lang) {
    const page = document.body.getAttribute("data-page"); // Get the page identifier
    if (!page) {
        console.error("Page identifier (data-page) is missing on the <body> tag.");
        return;
    }

    fetch(`/i18n/${page}/${lang}.json`)
        .then(response => response.json())
        .then(translations => {
            document.querySelectorAll("[data-translate]").forEach(element => {
                const key = element.getAttribute("data-translate");
                if (translations[key]) {
                    element.textContent = translations[key];
                }
            });
        })
        .catch(error => console.error("Error loading translations:", error));
}