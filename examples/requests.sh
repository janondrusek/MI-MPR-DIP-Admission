#!/bin/bash

##
## Admission
##

# Admission.getAll 
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" http://localhost:9090/admission/services/admission

# Admission.get 
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" http://localhost:9090/admission/services/admission/{admissionCode}

# Admission.add, uses example admission from XML file and POST request to add it 
cat admission_0[1-3].xml | curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -H "Content-type: application/xml" -X POST -d @- http://localhost:9090/admission/services/admission

# Admission.delete, simply deletes admission in cascade with all its entites
curl -i -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -X DELETE http://localhost:9090/admission/services/admission/{admissionCode}

# Admission.saveResult
cat admission_result.xml | curl -i -H "Accept: application/json" -H "Content-type: application/xml" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -X POST -d @- http://localhost:9090/admission/services/admission/{admissionCode}/result

# Admission.savePhoto
cat admission_photo.xml | curl -i -H "Accept: application/json" -H "Content-type: application/xml" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -X POST -d @- http://localhost:9090/admission/services/admission/{admissionCode}/photo

##
## User
##

# User.identity, username/password: more/more (DUMMY adapter)
curl -i -H "Accept: application/json" -H "Authorization: Basic bW9yZTptb3JlCg==" http://localhost:9090/admission/services/user/identity

# User.resetPassword, anonymous by User's {email}
curl -i -X POST http://localhost:9090/admission/services/user/person/email:{email}/reset_password

# User.resetPassword for User by Admission Code, send notification to User's Email and this {email}
curl -i -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -X POST http://localhost:9090/admission/services/user/admission/{admissionCode}/person/email:{email}/reset_password

# User.updatePassword
curl -i -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -X POST http://localhost:9090/admission/services/user/identity/{userIdentity}/password/old:{oldPassword}/new:{newPassword}

# User.deleteSession
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -X DELETE http://localhost:9090/admission/services/user/identity/{username}/session/identifier:[session identifier from User.identity]

# User.updateRoles
cat examples/user_roles.xml | curl -i -H "Accept: application/json" -H "Content-Type: application/xml" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -X POST -d @- http://localhost:9090/admission/services/user/identity/{username}/roles

##
## Term
##

# Term.getAll
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" http://localhost:9090/admission/services/term

# Term.get
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" http://localhost:9090/admission/services/term/dateOfTerm:{dateOfTerm}/room:{room}

# Term.add
cat examples/term_0[1-2].xml | curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -H "Content-type: application/xml" -X POST -d @- http://localhost:9090/admission/services/term

# Term.delete
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -X DELETE http://localhost:9090/admission/services/term/dateOfTerm:{dateOfTerm}/room:{room}

# Term.update, Term Registrations are ignored
cat examples/term_0[1-2].xml | curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -H "Content-type: application/xml" -X PUT -d @- http://localhost:9090/admission/services/term/ddateOfTerm:{dateOfTerm}/room:{room}

##
## Programme
##

# Programme.getAll
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" http://localhost:9090/admission/services/programme

# Programme.get
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" http://localhost:9090/admission/services/programme/name:{name}/degree:{degree}/language:{language}/studyMode:{studyMode}

# Programme.add
cat examples/programme_0[1-2].xml | curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -H "Content-type: application/xml" -X POST -d @- http://localhost:9090/admission/services/programme

# Programme.delete
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -X DELETE http://localhost:9090/admission/services/programme/name:{name}/degree:{degree}/language:{language}/studyMode:{studyMode}

# Programme.update, makes no sense as unique constraint contains all fields, update possible with equal resource only
cat examples/programme_0[1-2].xml | curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -H "Content-type: application/xml" -X PUT -d @- http://localhost:9090/admission/services/programme/name:{name}/degree:{degree}/language:{language}/studyMode:{studyMode}

##
## Registration
##

# Registration.add
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -H "Content-type: application/xml" -X POST http://localhost:9090/admission/services/admission/{admissionCode}/registration/term/dateOfTerm:{dateOfTerm}/room:{room}

# Registration.delete
curl -i -H "Accept: application/json" -H "X-CTU-FIT-Admission-Session: [session identifier from User.identity]" -X DELETE http://localhost:9090/admission/services/admission/{admissionCode}/registration/term/dateOfTerm:{dateOfTerm}/room:{room}
