import React from "react";

export default function OrderComplete({ summary }) {

  return (
    <div className="summary-section">
      <div></div>
      <div className="container mx-auto">
        <div class="container max-w-3xl px-4 mx-auto sm:px-8">
          <h1 className="text-4xl font-bold mb-5">Payment Completed</h1>
          <p>
            Thank you for your order! Your payment has been processed
            successfully.
          </p>
        </div>
      </div>

      <div className="container mx-auto">
        <div class="container max-w-3xl px-4 mx-auto sm:px-8">
          <div class="py-8">
            <div class="px-4 py-4 -mx-4 overflow-x-auto sm:-mx-8 sm:px-8">
              <div class="inline-block min-w-full overflow-hidden rounded-lg shadow">
                <table class="min-w-full leading-normal">
                  <thead>
                    <tr>
                      <th
                        scope="col"
                        class="px-5 py-3 text-sm font-normal text-left text-gray-800 uppercase bg-white border-b border-gray-200"
                      >
                         <strong>NAME</strong>
                      </th>
                      <th
                        scope="col"
                        class="px-5 py-3 text-sm font-normal text-left text-gray-800 uppercase bg-white border-b border-gray-200"
                      >
                         <strong>EMAIL</strong>
                      </th>
                      <th
                        scope="col"
                        class="px-5 py-3 text-sm font-normal text-left text-gray-800 uppercase bg-white border-b border-gray-200"
                      >
                         <strong>CARD</strong>
                      </th>
                      <th
                        scope="col"
                        class="px-5 py-3 text-sm font-normal text-left text-gray-800 uppercase bg-white border-b border-gray-200"
                      >
                         <strong>ZIP</strong>
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      {summary.map((item, index) => (
                        <td
                          key={index}
                          className="px-5 py-5 text-sm bg-white border-b border-gray-200"
                        >
                          <div className="flex items-center">
                            <p className="text-gray-900 whitespace-no-wrap">
                              {item.value}
                            </p>
                          </div>
                        </td>
                      ))}
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
