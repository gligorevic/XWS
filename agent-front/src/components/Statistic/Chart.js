import React from "react";
import { useTheme } from "@material-ui/core/styles";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Label,
  ResponsiveContainer,
} from "recharts";
import Typography from "@material-ui/core/Typography";
import { format } from "date-fns";

// Generate Sales Data

export default function Chart({ statistic }) {
  const theme = useTheme();
  function createData(time, amount) {
    return { time, amount };
  }

  const data = [
    Object.entries(statistic.requestsPerDay).map((row) =>
      createData(format(new Date(row[0]), "dd MMM yyyy"), row[1])
    ),
  ];

  return (
    <React.Fragment>
      <Typography component="h2" variant="h6" color="secondary" gutterBottom>
        Number of requests
      </Typography>
      <ResponsiveContainer>
        <LineChart
          data={data[0]}
          margin={{
            top: 16,
            right: 16,
            bottom: 0,
            left: 24,
          }}
        >
          <XAxis dataKey="time" stroke={theme.palette.text.secondary} />
          <YAxis stroke={theme.palette.text.secondary}>
            <Label
              angle={270}
              position="left"
              style={{ textAnchor: "middle", fill: theme.palette.text.primary }}
            >
              Number of requests
            </Label>
          </YAxis>
          <Line
            type="monotone"
            dataKey="amount"
            stroke={theme.palette.primary.main}
            dot={false}
          />
        </LineChart>
      </ResponsiveContainer>
    </React.Fragment>
  );
}
