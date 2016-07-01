import net from 'net'
import vorpal from 'vorpal'
import { createLoginCmds, deleteAuthCmds, createUser } from './lib'

const cli = vorpal()
const port = 670
const host = 'localhost'
let flag = true
let server

cli.delimiter('auth:')

const register = cli.command('register <username> <password>')
  .description('Registers a user with a username and pasword')
  .action(function (args, callback) {
    server = net.createConnection(port, host, () => { return 0 })
    server.write(JSON.stringify({'user': createUser(args.username, args.password)}) + '\n')
    // server.write(`user ${args.username} ${args.password}`)
    this.log('Registered? maybe?')
    server.end()
    callback()
  })

const login = cli.command('login <username> <password>')
  .description('Logs in using an existing username and password')
  .action(function (args, callback) {
    server = net.createConnection(port, host, () => { return 0 })
    server.write(JSON.stringify({'login': createUser(args.username, args.password)}) + '\n')
    // server.on('data', (d) => {
    //   let hash = d.toString()
    //   if (hash === 'true') {
    server.end()
      if (flag) {
        //server.end()
        this.log('Logged in success!')
        createLoginCmds(cli)
        deleteAuthCmds([register, login])
      } else {
        this.log('Username or password is incorrect.')
      }
    // })
    callback()
  })

export default cli
