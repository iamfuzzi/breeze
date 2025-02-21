document.addEventListener("DOMContentLoaded", function () {
    const themes = ["light", "dark", "pastel"];
    const themeToggleBtn = document.getElementById("theme-toggle");

    // Устанавливаем текущую тему как атрибут на <html> сразу, чтобы избежать мигания
    let currentTheme = localStorage.getItem("theme") || "light";
    document.documentElement.setAttribute("data-theme", currentTheme);

    // При клике на кнопку переключаем тему
    themeToggleBtn.addEventListener("click", function () {
        let newTheme = themes[(themes.indexOf(currentTheme) + 1) % themes.length];
        setTheme(newTheme);
    });

    function setTheme(theme) {
        document.documentElement.setAttribute("data-theme", theme);
        localStorage.setItem("theme", theme);
        currentTheme = theme;
    }
});
