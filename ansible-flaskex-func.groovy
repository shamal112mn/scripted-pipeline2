properties([
    parameters([
        string(defaultValue: '', description: 'Provide node IP', name: 'node', trim: true)
        ])
    ])
def myplaybook(playbookName){
    ansiblePlaybook become: true, colorized: true, credentialsId: 'jenkins-master-key', disableHostKeyChecking: true, inventory: "${params.node},", playbook: "${playbookName}"
}
node{
    stage("Pull Repo"){
        git url: 'https://github.com/ikambarov/ansible-Flaskex.git'
    }
    stage("Install Prerequisites"){
       myplaybook("prerequisites.yml")
    }
     
}
