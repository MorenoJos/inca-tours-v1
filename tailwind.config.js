/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/main/resources/templates/**/*.html",  // tus vistas Thymeleaf
    "./src/main/resources/static/**/*.js",       // scripts JS si tienes
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}

