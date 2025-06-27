import React, { useState } from 'react';

export default function SubmissionsTable({ data, onEdit, onDelete }) {
  const [filter, setFilter] = useState('');

  const needle = filter.toLowerCase();
  const filtered = data.filter((item) => {
    const name = item.fullName ?? '';
    const email = item.email ?? '';
    const idStr = String(item.id);
    return (
      idStr.includes(needle) ||
      name.toLowerCase().includes(needle) ||
      email.toLowerCase().includes(needle)
    );
  });

  return (
    <div className="table-container">
      <input
        placeholder="Search by ID, name, or email..."
        value={filter}
        onChange={(e) => setFilter(e.target.value)}
      />
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Age</th>
            <th>Contact</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filtered.map((row) => (
            <tr key={row.id}>
              <td>{row.id}</td>
              <td>{row.fullName}</td>
              <td>{row.email}</td>
              <td>{row.phoneNumber}</td>
              <td>{row.age}</td>
              <td>{row.preferredContact}</td>
              <td>
                <button onClick={() => onEdit(row)}>Edit</button>
                <button onClick={() => onDelete(row.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
