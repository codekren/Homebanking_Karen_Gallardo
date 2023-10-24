const { createApp } = Vue

createApp({
  data() {
    return {
      dataClient:{},
      dataLoans:[],      
      
      
    }
  },
  created(){
    this.loadData();    
    

  },

  methods:{
    logOut(){
      axios.post('/api/logout')
      .then(response => {
        console.log('signed out!!!')
        window.location.href = '/web/index.html';
      })
      .catch(err=>console.log("error"))
     },

    loadData(){
      axios('http://localhost:8080/api/clients/current')
      .then(datos=> {
        this.dataClient=datos.data 
        this.dataLoans=this.dataClient.loans
        


      })
      .catch(err=> console.log('error'))
  },
    createAccount(){
      axios.post('/api/clients/current/accounts')
      .then(response=>{
        console.log("created")
        location.reload();
        
      })
      .catch(err=> console.log("err"))

    }
  
  }
}).mount('#app')