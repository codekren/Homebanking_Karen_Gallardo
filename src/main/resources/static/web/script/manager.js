const { createApp } = Vue

createApp({
  data() {
    return {
        dataClient:{},
        name:"",
        lastName:"",
        email:"",
        mensaje:"",
        newClient:{},
    }
  },
  created(){
    this.loadData()

  },

  methods:{
    loadData(){
        axios('http://localhost:8080/api/clients')
        .then(datos=> this.dataClient=datos.data )
        .catch(err=> console.log('error'))
    },
    addClient(){
        let newName=this.name
        let newLastName=this.lastName
        let newEmail=this.email
       
        if (newName && newLastName && newEmail){            
            this.newClient={
                name:newName,
                lastName:newLastName,
                email:newEmail,
            } 
            this.postClient()

        } else{
            this.mensaje = "complete toda la información solicitada"
            return 

        }              
        

    },
    postClient(){
        axios.post('http://localhost:8080/clients', this.newClient)
        .then(response=> {
            this.dataClient._embedded.clients.push(this.newClient)
            this.loadData()
        })
        .catch(err=> console.log("error"))

        this.name=""
        this.lastName=""
        this.email=""
        this.mensaje=""


    }
    

  }

  
}).mount('#app')