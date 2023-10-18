const { createApp } = Vue

createApp({
  data() {
    return {
      dataClient:{},
      cardDebits:[],
      cardCredits:[],
      
    }
  },
  created(){
    this.loadData();
    
    

  },

  methods:{
    loadData(){
      axios('http://localhost:8080/api/clients/current')
      .then(datos=> {
        this.dataClient=datos.data 
        this.cardDebits= this.dataClient.cards.filter(card=> card.type=="DEBIT")
        this.cardCredits= this.dataClient.cards.filter(card=> card.type=="CREDIT")

      

      })
      .catch(err=> console.log('error'))
  },
  formatDate(date) {
    return new Date(date).toLocaleString('en-US', { month: '2-digit', year: '2-digit' });
    },

  logOut(){
      axios.post('/api/logout')
      .then(response => {
        console.log('signed out!!!')
        window.location.href = '/web/index.html';
      })
      .catch(err=>console.log("error"))
     }
  }
}).mount('#app')