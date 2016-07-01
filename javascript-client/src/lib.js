import bcrypt from 'bcryptjs'

export function createLoginCmds (cli) {
  cli.command('files')
    .description('Displays your stored files.')
    .action(function (args, callback) {
      this.log('Files displayed')
      callback()
    })

  cli.command('upload <local_path> [database_path]')
    .description('Uploads to the server a file from the local path.')

  cli.command('download <database_id> [local_path]')
    .description('Downloads a file from the server with the specified id to the path specified or default path.')
}

export function deleteAuthCmds (cmds) {
  for (let i = 0; i < cmds.length; i++) {
    cmds[i].remove()
  }
}

export function createUser (username, password) {
  return {
    'username': username,
    'password': bcrypt.hashSync(password)
  }
}

export function hashPass (password) {
  return new Promise(function (resolve, reject) {
    bcrypt.genSalt(function (err, salt) {
      if (err) reject(err)
      else {
        bcrypt.hash(password, salt, function (err, hashedPass) {
          if (err) reject(err)
          else resolve(hashedPass)
        })
      }
    })
  })
}
//
// export function comparePass (pass, hashedPass) {
//   return new Promise(function (resolve, reject) {
//     bcrypt.compare(pass, hashedPass, function (err, success) {
//       if (err) reject(err)
//       else resolve(success)
//     })
//   })
// }

export default {
  createLoginCmds,
  deleteAuthCmds,
  hashPass,
  createUser
  // comparePass
}
