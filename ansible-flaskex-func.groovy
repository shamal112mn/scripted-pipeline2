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
       myplaybook('prerequisites.yml')
    }
    withEnv(['FLASKEX_REPO=https://github.com/ikambarov/Flaskex.git', 'FLASKEX_BRANCH=master']) {
        stage("Pull Repo"){
            myplaybook ('pull_repo.yml')
        }
    }
     
    stage("Install Python"){
        myplaybook ('install_python.yml')
    }  
    stage("Start App"){
        myplaybook ('start_app.yml')
    } 
     
}
