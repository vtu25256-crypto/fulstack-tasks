document.addEventListener('DOMContentLoaded', () => {
    const productForm = document.getElementById('productForm');
    const productTable = document.querySelector('#productTable tbody');
    const auditLog = document.getElementById('auditLog');
    const reportContainer = document.getElementById('reportContainer');

    const fetchProducts = async () => {
        const response = await fetch('api.php?action=get_products');
        const products = await response.json();
        productTable.innerHTML = products.map(p => `
            <tr>
                <td>${p.name}</td>
                <td>
                    <input type="number" value="${p.stock}" 
                           onchange="updateStock(${p.id}, this.value)" 
                           style="width: 60px; background: transparent; border: 1px solid rgba(255,255,255,0.1)">
                </td>
                <td>$${parseFloat(p.price).toFixed(2)}</td>
                <td><small>${p.category}</small></td>
            </tr>
        `).join('');
    };

    const fetchLogs = async () => {
        const response = await fetch('api.php?action=get_logs');
        const logs = await response.json();
        auditLog.innerHTML = logs.map(l => `
            <div class="log-entry ${l.action_type}">
                <div class="log-time">${new Date(l.changed_at).toLocaleString()}</div>
                <strong>${l.action_type}</strong>: Record #${l.record_id} in ${l.table_name}<br>
                ${l.old_values ? `<small>OLD: ${l.old_values}</small><br>` : ''}
                <small>NEW: ${l.new_values}</small>
            </div>
        `).join('');
    };

    const fetchReport = async () => {
        const response = await fetch('api.php?action=get_report');
        const reports = await response.json();
        reportContainer.innerHTML = reports.map(r => `
            <div class="report-item">
                <strong>${r.report_date}</strong>: ${r.total_actions} actions (${r.action_type})
            </div>
        `).join('');
    };

    window.updateStock = async (id, stock) => {
        await fetch('api.php?action=update_stock', {
            method: 'POST',
            body: JSON.stringify({ id, stock })
        });
        fetchLogs();
        fetchReport();
    };

    productForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const data = {
            name: document.getElementById('name').value,
            category: document.getElementById('category').value,
            stock: document.getElementById('stock').value,
            price: document.getElementById('price').value
        };

        await fetch('api.php?action=add_product', {
            method: 'POST',
            body: JSON.stringify(data)
        });

        productForm.reset();
        fetchProducts();
        fetchLogs();
        fetchReport();
    });

    // Initial load
    fetchProducts();
    fetchLogs();
    fetchReport();

    // Refresh logs periodically
    setInterval(() => {
        fetchLogs();
        fetchReport();
    }, 5000);
});
