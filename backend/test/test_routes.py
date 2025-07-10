
def test_all_submissions(client):
    response = client.get("/api/submissions")
    
    assert response.status_code ==200

def test_submisison_by_id(client,test_db):
    response = client.get("/api/submissions/1")
    data = response.get_json()
    assert data["id"]==1
    assert data["fullName"]=="test1 test"

def test_update_submission_by_id(client,test_db):
    payload={
        "fullName":"test1 test test",
        "age":20,
        "phoneNumber":"1111111111",
        "preferredContact":"both",
        "email":"test11@test.com"
    }
    response = client.put("/api/submissions/1", json = payload)
    data = response.get_json()
    assert data["fullName"]==payload["fullName"]
    assert data["preferredContact"]==payload["preferredContact"]
    assert data["email"]==payload["email"]

def test_create_submission(client,test_db):
    payload = {
        "fullName":"test3 test",
        "age":27,
        "phoneNumber":"3333333333",
        "preferredContact":"phone",
        "email":"test3@test.com"
    }
    response = client.post("/api/submissions",json=payload)
    data = response.get_json()
    assert response.status_code==201
    assert data["id"] == 3


def test_delete_submission(client,test_db):
    response = client.delete("/api/submissions/2")
    data = response.get_json()
    assert data["message"]=="Submission deleted successfully"
