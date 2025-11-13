import { render, screen } from '@testing-library/react';
import App from '../App';

test('renders Combined Spring + React Template heading', () => {
  render(<App />);
  const heading = screen.getByText(/Combined Spring \+ React Template/i);
  expect(heading).toBeInTheDocument();
});
