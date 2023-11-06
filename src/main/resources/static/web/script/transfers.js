const { createApp } = Vue

createApp({
  data() {
    return {
      clientAccounts:[],       
      originAccount:"",
      finalAccount:"",
      amount:0,
      destinationType:"",
      description:"",
      transactions:"",
      
      accountId:"",
      
    }
  },
  created(){
    this.loadData();    
    
  },
  computed:{
  filteredAccounts() {
    // Filtrar las cuentas para que no incluyan la cuenta de origen
    return this.clientAccounts.filter(account => account.number !== this.originAccount);
  }
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
      axios('/api/clients/current/accounts')
      .then(datos=> {
        this.clientAccounts=datos.data 

      })
      .catch(err=> console.log('error'))
  },
    newTransaction(){
   
    this.transactions = `amount=${this.amount}&description=${this.description}&numberBegin=${this.originAccount}&numberFinal=${this.finalAccount}`
      
    axios.post('/api/transactions',this.transactions)
    .then(response =>{
      this.accountId = response.data
      console.log(this.accountId)
      window.location.href = '/web/pages/account.html?id='+this.accountId  
      this.originAccount = ""
      this.finalAccount = ""
      this.destinationType = ""
      this.description = "" 
      this.amount = 0 
        
    } )
    .catch(err =>{
  
      
    console.log(err)}
    )
    },
  }
}).mount('#app')