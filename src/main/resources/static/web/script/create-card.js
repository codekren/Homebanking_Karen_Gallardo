const { createApp } = Vue;

createApp({
  data() {
    return {
      cardType: "",
      cardColor: "",
      errorMessage: "",
      cardData:"",
    };
  },
  methods: {
    returnCards(){
      location.href = "./cards.html";

    },

    createCard() {
      // Realiza la solicitud POST para crear la tarjeta
      this.cardData = `cardColor=${this.cardColor}&cardType=${this.cardType}`;
   
     
      axios.post("/api/clients/current/cards", this.cardData)
        .then(() => {
          // Redirige a cards.html en caso de éxito
          window.location.pathname = "/web/pages/cards.html";
        })
        .catch((error) => {
          // Muestra un mensaje de error en caso de fallo
          this.errorMessage = "Error al crear la tarjeta. Por favor, inténtalo de nuevo.";
        });
    },
  },
}).mount("#app");
