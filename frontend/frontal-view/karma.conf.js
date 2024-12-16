module.exports = function (config) {
  config.set({
    // Otras configuraciones...
    browsers: ['ChromeHeadless'], // Ejecuta Chrome en modo headless
    singleRun: true, // Karma se detiene después de completar las pruebas
    restartOnFileChange: true, // Reinicia pruebas al cambiar archivos
  });
};