
from sqlalchemy import *
from datetime import datetime
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class Submission(db.Model): # tells the SQL-ALCHEMY to trat this as a table not a regualr python class
    __tablename__ = 'submissions'

    id = Column(Integer,primary_key=True)
    fullName = Column("full_name",String ,nullable=False)
    email = Column("email",String, unique=True, nullable=False)
    phoneNumber = Column("phone_number",String(10), nullable = False)
    preferredContact = Column("preferred_contact",String,nullable=False)
    age = Column("age",Integer, nullable=False)
    address = Column("address",String)
    createdAt = Column("created_at",DateTime, default= datetime.now)
    updatedAt = Column("updated_at",DateTime, default= datetime.now,onupdate=datetime.now)
    
    def convertToDict(self):
        return {
            "id": self.id,
            "fullName":self.fullName,
            "email":self.email,
            "phoneNumber":self.phoneNumber,
            "age":self.age,
            "address":self.address,
            "preferredContact":self.preferredContact,
            "createdAt":self.createdAt,
            "updatedAt":self.updatedAt

        }


