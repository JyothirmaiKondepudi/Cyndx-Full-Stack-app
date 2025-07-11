import pytest
from app import create_app
from model import Submission, db as _db
import os
from config import TestingConfig

@pytest.fixture
def app():
    os.environ["FLASK_ENV"] = "testing"
    app = create_app(TestingConfig)
    app.config['TESTING'] = True

    with app.app_context():
        _db.create_all()
        yield app
        _db.session.remove()
        _db.drop_all()

@pytest.fixture
def client(app):
    return app.test_client()

@pytest.fixture
def test_db(app):
    with app.app_context():
        submission1 = Submission()
        submission1.fullName ="test1 test"
        submission1.age =20
        submission1.phoneNumber ="1111111111"
        submission1.preferredContact = "email"
        submission1.email="test1@test.com"
        submission1.address = "test ave, test city"

        submission2 = Submission()
        submission2.fullName ="test2 test"
        submission2.age =21
        submission2.phoneNumber ="9999999999"
        submission2.preferredContact = "both"
        submission2.email="test2@test.com"
        submission2.address="test ave, test city, test state"

        _db.session.add_all([submission1,submission2])
        _db.session.commit()
    yield app