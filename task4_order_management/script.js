document.addEventListener('DOMContentLoaded', () => {
    const historyTableBody = document.getElementById('historyTableBody');
    const topOrder = document.getElementById('topOrder');
    const topCustomer = document.getElementById('topCustomer');

    const fetchHistory = async () => {
        try {
            const response = await fetch('api.php?action=get_history');
            const data = await response.json();
            
            historyTableBody.innerHTML = '';
            data.forEach(order => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>#${order.order_id}</td>
                    <td>${order.customer_name}</td>
                    <td>${order.product_name}</td>
                    <td>${order.order_date}</td>
                    <td><strong>$${parseFloat(order.total_amount).toLocaleString()}</strong></td>
                `;
                historyTableBody.appendChild(row);
            });
        } catch (error) {
            console.error('Error history:', error);
        }
    };

    const fetchInsights = async () => {
        try {
            const response = await fetch('api.php?action=get_insights');
            const data = await response.json();
            
            if (data.highest_order) {
                topOrder.innerHTML = `${data.highest_order.customer_name} ($${data.highest_order.total_amount})<br><small>${data.highest_order.product_name}</small>`;
            }
            if (data.most_active) {
                topCustomer.innerHTML = `${data.most_active.name}<br><small>${data.most_active.order_count} Orders Total</small>`;
            }
        } catch (error) {
            console.error('Error insights:', error);
        }
    };

    // Initial load
    fetchHistory();
    fetchInsights();
});
