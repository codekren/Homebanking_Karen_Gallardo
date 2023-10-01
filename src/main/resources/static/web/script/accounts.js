const { createApp } = Vue

createApp({
  data() {
    return {
      dataClient:{},
    }
  },
  created(){
    this.loadData();

  },

  methods:{
    loadData(){
      axios('http://localhost:8080/api/clients/1')
      .then(datos=> {
        this.dataClient=datos.data 
      })
      .catch(err=> console.log('error'))
  },
  }
}).mount('#app')