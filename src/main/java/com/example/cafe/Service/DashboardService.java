package com.example.cafe.Service;

import com.example.cafe.Repository.Order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final OrderRepository orderRepository;

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalRevenue", orderRepository.getActualRevenue());
        stats.put("totalOrders", orderRepository.getTotalOrdersCount());
        stats.put("totalCustomers", orderRepository.getTotalUniqueCustomers());

        List<Object[]> data = orderRepository.getRevenueByDay();
        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for (Object[] row : data) {
            labels.add(row[0].toString());
            values.add(((Number) row[1]).doubleValue());
        }

        Map<String, Object> chartData = new HashMap<>();
        chartData.put("labels", labels);
        chartData.put("datasets", values);

        stats.put("chartData", chartData);

        return stats;
    }
}
