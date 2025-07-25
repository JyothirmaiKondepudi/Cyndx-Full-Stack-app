import React, { useState, useEffect } from 'react';
import SubmissionForm from './components/SubmissionForm';
import SubmissionsTable from './components/SubmissionsTable';
// const API_BASE = "https://mw24u3yry6.execute-api.us-east-1.amazonaws.com/prod";
const API_BASE = "https://xaywh30zr8.execute-api.us-east-1.amazonaws.com/production";

export default function App() {
  const [submissions, setSubmissions] = useState([]);
  const [editing, setEditing] = useState(null);

  const fetchAll = async () => {
    try {
      console.log("Fetching from:", `${API_BASE}/api/submissions`);
      const res = await fetch(`${API_BASE}/api/submissions`);
      if (!res.ok) throw new Error(res.statusText);
      setSubmissions(await res.json());
    } catch (err) {
      alert(`Fetch failed: ${err.message}`);
    }
  };

  useEffect(() => {
    fetchAll();
  }, []);

  const handleCreate = async (data) => {
    try {
      const res = await fetch(`${API_BASE}/api/submissions`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      if (!res.ok) {
        const txt = await res.text();
        throw new Error(`HTTP ${res.status}: ${txt}`);
      }
      await fetchAll();
    } catch (err) {
      alert(`Create failed: ${err.message}`);
    }
  };

  const handleUpdate = async (id, data) => {
    try {
      const res = await fetch(`${API_BASE}/api/submissions/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
      });
      if (!res.ok) {
        const txt = await res.text();
        throw new Error(`HTTP ${res.status}: ${txt}`);
      }
      setEditing(null);
      await fetchAll();
    } catch (err) {
      alert(`Update failed: ${err.message}`);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Really delete?')) return;
    try {
      const res = await fetch(`${API_BASE}/api/submissions/${id}`, {
        method: 'DELETE',
      });
      if (!res.ok) throw new Error(res.statusText);
      await fetchAll();
    } catch (err) {
      alert(`Delete failed: ${err.message}`);
    }
  };

  return (
    <div className="container">
      <h1>Form Submissions</h1>

      <SubmissionForm
        key={editing ? editing.id : 'new'}
        initialData={editing}
        onCreate={handleCreate}
        onUpdate={handleUpdate}
        onCancel={() => setEditing(null)}
      />

      <SubmissionsTable
        data={submissions}
        onEdit={(item) => setEditing(item)}
        onDelete={handleDelete}
      />
    </div>
  );
}
