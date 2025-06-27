// src/components/SubmissionForm.jsx
import React, { useState, useEffect } from 'react';

const empty = {
  fullName: '',
  email: '',
  phoneNumber: '',
  age: '',
  address: '',
  preferredContact: 'Email',
};

export default function SubmissionForm({
  initialData,
  onCreate,
  onUpdate,
  onCancel,
}) {
  const [form, setForm] = useState(empty);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (initialData) {
      setForm({ ...initialData });
    } else {
      setForm(empty);
    }
  }, [initialData]);

  const validate = () => {
    const errs = {};
    if (!form.fullName.trim()) errs.fullName = 'Required';
    if (!/\S+@\S+\.\S+/.test(form.email)) errs.email = 'Invalid email';
    if (!/^\d{10}$/.test(form.phoneNumber.replace(/\D/g, '')))
      errs.phoneNumber = 'Enter 10 digits';
    const age = Number(form.age);
    if (!age || age < 18 || age > 120) errs.age = 'Age 18–120';
    setErrors(errs);
    return !Object.keys(errs).length;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;
    const payload = { ...form, age: Number(form.age) };
    if (initialData) await onUpdate(initialData.id, payload);
    else await onCreate(payload);
    setForm(empty);
  };

  return (
    <form onSubmit={handleSubmit} className="form">
      {/* ID (read-only when editing) */}
      {initialData && (
        <>
          <label htmlFor="id">ID</label>
          <input id="id" value={initialData.id} disabled />
        </>
      )}

      <label htmlFor="fullName">Full Name*</label>
      <input
        id="fullName"
        value={form.fullName}
        onChange={(e) => setForm({ ...form, fullName: e.target.value })}
      />
      {errors.fullName && <span className="error">{errors.fullName}</span>}

      <label htmlFor="email">Email*</label>
      <input
        id="email"
        type="email"
        value={form.email}
        onChange={(e) => setForm({ ...form, email: e.target.value })}
      />
      {errors.email && <span className="error">{errors.email}</span>}

      <label htmlFor="phoneNumber">Phone Number*</label>
      <input
        id="phoneNumber"
        value={form.phoneNumber}
        onChange={(e) => setForm({ ...form, phoneNumber: e.target.value })}
      />
      {errors.phoneNumber && <span className="error">{errors.phoneNumber}</span>}

      <label htmlFor="age">Age*</label>
      <input
        id="age"
        type="number"
        value={form.age}
        onChange={(e) => setForm({ ...form, age: e.target.value })}
      />
      {errors.age && <span className="error">{errors.age}</span>}

      <label htmlFor="address">Address</label>
      <textarea
        id="address"
        value={form.address}
        onChange={(e) => setForm({ ...form, address: e.target.value })}
      />

      <label htmlFor="preferredContact">Preferred Contact*</label>
      <select
        id="preferredContact"
        value={form.preferredContact}
        onChange={(e) =>
          setForm({ ...form, preferredContact: e.target.value })
        }
      >
        <option>Email</option>
        <option>Phone</option>
        <option>Both</option>
      </select>

      {/* Buttons span both columns */}
      <button type="submit">
        {initialData ? 'Update' : 'Submit'}
      </button>
      {initialData && (
        <button type="button" onClick={onCancel}>
          Cancel
        </button>
      )}
    </form>
  );
}
