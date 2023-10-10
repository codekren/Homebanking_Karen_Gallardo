const { createApp } = Vue

createApp({
  data() {
    return {
      dataAccount:{},
      parametro:null,
      idAccount:0,
     

    }
  },
  created(){
    
    this.parametro=location.search;
    let params= new URLSearchParams (this.parametro) ;
    this.idAccount =params.get("id") ;
    this.loadData();
    
    

  },

  methods:{
    loadData(){
      axios('http://localhost:8080/api/accounts/'+this.idAccount)
      .then(datos=> {
        this.dataAccount=datos.data
        
      })
      .catch(err=> console.log('error'))
  },
  }
}).mount('#app')