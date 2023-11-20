const { createApp } = Vue;

createApp({
  data() {
    return {
      loanName: "",
      maxAmount: 0,
      percentage:0.0,
      payments:[],
      
    
    };
  },
  methods: {

    newLoan() {
      
      let arrayPayments= this.payments.split(',').map(payment => parseInt(payment, 10)).sort((payment1, payment2) => payment1 - payment2);
      console.log(arrayPayments);
      let createLoan = {loanName:this.loanName,maxAmount:this.maxAmount,percentage:this.percentage,payments:arrayPayments};
      console.log(createLoan);
      axios.post("/api/loans/create", createLoan)
        .then(() => {
          
          
        })
        .catch((error) => {
            console.log(error)
          
          
        });
    }
  },
}).mount("#app");
