#app.py

from flask import Flask, request, jsonify
from dotenv import load_dotenv
from sqlalchemy import *
from config import *
from flask_cors import CORS
from model import Submission, db


load_dotenv()

def create_app(config_class = DevelopmentConfig):
    flask_app= Flask(__name__)
    CORS(flask_app)
    flask_app.config.from_object(config_class)
    db.init_app(flask_app)

    @flask_app.get("/api/health")
    def getHealthCheck():
        return jsonify({
            "message":"API is healthy",
            "status":200
        }),200
        
    @flask_app.get("/api/submissions")
    def getAllSubmissions():
        submissions = Submission.query.all()
        
        return jsonify([s.convertToDict() for s in submissions]),200

    @flask_app.get("/api/submissions/<int:id>")
    def getSubmissionByID(id):
        try:
            submission = Submission.query.get_or_404(id)

        except:
            return jsonify({
                "error":"Not Found",
                "message":f"Form id: {id} not found".format(id),
                "status":404

            }),404
       
        return jsonify(submission.convertToDict()),200

    @flask_app.post("/api/submissions")
    def createSubmission():
        data = request.get_json() or {}
        fields = ("fullName","email","age","preferredContact","phoneNumber")
        for f in fields:
            if f not in data:
                return jsonify({
                    "error":"Invalid Request",
                    "message":"Missing one or more request parameters",
                    "status": 400
                }),400
        if Submission.query.filter_by(email=data["email"]).first():
            return jsonify({
                "error":"Conflict",
                "message":f"Email already exists: {data['email']}",
                "status":409
            }),409
        newSubmission = Submission(
            fullName=data["fullName"],
            age = data["age"],
            preferredContact=data["preferredContact"],
            phoneNumber=data["phoneNumber"],
            email=data["email"],
            address = data["address"]
        )
        db.session.add(newSubmission)
        db.session.commit()

        return jsonify(newSubmission.convertToDict()),201

    @flask_app.put("/api/submissions/<int:id>")
    def updateSubmissionByID(id):
        try:
            submission = Submission.query.get_or_404(id)
        except:
            return jsonify({
                "error":"Not Found",
                "message":f"Form id: {id} not found".format(id),
                "status":404

            }),404
        data = request.get_json()
        fields = ("fullName","email","age","preferredContact","phoneNumber")
        for f in fields:
            if f not in data:
                return jsonify({
                    "error":"Invalid Request",
                    "message":"Missing one or more request parameters",
                    "status":400}),400
            else:
                if data[f]=="":
                    return jsonify({
                    "error":"Invalid Request",
                    "message":f"Missing value for the field {f}",
                    "status":400
                    }),400

        submission.fullName= data["fullName"]
        submission.age=data["age"]
        submission.preferredContact= data["preferredContact"]
        submission.phoneNumber=data["phoneNumber"]
        submission.email=data["email"]
        submission.address=data["address"]

        db.session.commit()
        return jsonify(submission.convertToDict()),200

    @flask_app.delete("/api/submissions/<int:id>")
    def deleteSubmissionById(id):
        try:
            submission = Submission.query.get_or_404(id)
        except:
            return jsonify({
                "error":"Not Found",
                "message":f"Form id: {id} not found".format(id),
                "status":404

            }),404
        db.session.delete(submission)
        db.session.commit()
        return jsonify({
            "message":"Submission deleted successfully",
            "id":id,
            "status":200
        }),200
    return flask_app

if __name__ == "__main__":
    app = create_app()
    app.run(debug=True)

